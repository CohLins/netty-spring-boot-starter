最近出于学习的目的，想整个springboot-starter玩玩，然后又正好在玩netty，所以就决定搞一个netty-springboot-starter，目的主要是学习，初步设想有以下功能：

- 可以通过配置一键启动服务端或者客户端
- 通过注解就可以实现自己的数据处理Handler
- 内置协议，可以通过配置进行切换如：http、webSocket、自定义协议等
- 内置心跳机制、重连机制

目前以上功能基本实现，接下来看看功能使用介绍

# 一、使用

我们以服务端来举例，可打包到本地仓库引入使用

## 服务端

需要配置启动参数，协议那里如果不自定义则必填否则服务端不会启动(怎么自定义后面说)，下面我们介绍内置的三种协议的配置启动

![](https://secure2.wostatic.cn/static/iWcLQuGGr1zLbcttgrTTqQ/image.png?auth_key=1678349817-ujvhbAjzbmesvCpUK65tJK-0-f8c852fefabd6178964514bfbff6ef93)

### 内嵌协议

#### HTTP

http-config为http参数配置项，不配也可以有默认值

配置如下：

![](https://secure2.wostatic.cn/static/fAKBcbnb5weEnvUAS7iPnJ/image.png?auth_key=1678349817-sZLX3Nvv5shwiv1AmVXGg2-0-e325b889735460d303996e58ed68e436)

数据处理类自己编写，如同netty一样，只需要打上 **@NettyServerHandler** 注解即可，如：

```Java
@NettyServerHandler
public class HttpBaseHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final static Logger log= LoggerFactory.getLogger(HttpBaseHandler.class);


    @Resource
    private ApplicationEventPublisher applicationContext;

    public HttpBaseHandler(ApplicationEventPublisher applicationContext){
        this.applicationContext=applicationContext;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest t) throws Exception {
        log.info("http请求 uri：{}，method：{}",t.uri(),t.method());

        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.content().writeBytes(JSON.toJSONBytes("Hello"));
        response.headers().add(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON+";charset=UTF-8");
        response.headers().add(HttpHeaderNames.CONTENT_LENGTH,response.content().readableBytes());
        channelHandlerContext.writeAndFlush(response);
    }
}

```

启动springboot项目测试：

![](https://secure2.wostatic.cn/static/jJVon5XyghPvjQJ2A3w2o/image.png?auth_key=1678349817-qtcpE4ag7sRpWVJr5ELZNw-0-1dd7747ed72606f6c9886f64304212d9)

打开浏览器，访问127.0.0.1:8888，就可以看到请求结果了

![](https://secure2.wostatic.cn/static/bnrzDdWypbC6QUGhZBZzVd/image.png?auth_key=1678349816-pLAD124ts1G3W2iJrqmEGG-0-8714c0a50b87be4df02988987bb4e654)

![](https://secure2.wostatic.cn/static/wTuXfXXjcxZSFF4BQZX8Ck/image.png?auth_key=1678349816-vn616ZtfPVJbhy7qqHzZhD-0-3392497de598c1b9127d47885ab3965e)

#### WebSocket

由于WebSocket也同样会采用Http编解码，所以http的配置也同时会满足WebSocket，一样都有默认值，可以不配置

配置如下：

![](https://secure2.wostatic.cn/static/5EvvT5znRqoWyXDySp2md5/image.png?auth_key=1678349816-kNjUJn2kUNaBjGHedvg9yM-0-ba359af7c2eeb103429e52caf0fb5c22)

数据处理类自己编写，如同netty一样，只需要打上 **@NettyServerHandler** 注解即可，如：

```Java
@NettyServerHandler
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final static Logger log= LoggerFactory.getLogger(WebSocketHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info("建立连接");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        log.info("收到消息："+textWebSocketFrame.text());
        channelHandlerContext.writeAndFlush(new TextWebSocketFrame(textWebSocketFrame.text()));
    }
}
```

我们准备一个WebSocket的客户端 html ：

```Java
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html";charset="UTF-8" />
    <title>Netty WebSocket测试</title>
    <script type="text/javascript">
        var socket;
        if (!window.WebSocket) {
            window.WebSocket = window.MozWebSocket;
        }
        if (window.WebSocket) {
            socket = new WebSocket("ws://localhost:8888/ws");
            socket.onmessage = function (event) {
                var ta = document.getElementById('responseText');
                ta.value = "";
                ta.value=event.data;
            };
            socket.onopen = function (event) {
                var ta = document.getElementById('responseText');
                ta.value = "已连接";
            };

            socket.onclose = function (event) {
                var ta = document.getElementById('responseText');
                ta.value = "已关闭";
            };
        } else {
            alert("您的浏览器不支持WebSocket协议！");
        }

        function send(message) {
            if (!window.WebSocket) {
                return;
            }
            if (socket.readystate = WebSocket.OPEN) {
                socket.send(message);
            } else {
                alert("WebSocket连接没有建立成功！");
            }
        }

    </script>
</head>
<body>
<form onSubmit="return false;" ;>
    <hr color="black"/>
    <h3>客户端发送的信息</h3>
    <label>消息</label><input type="text" name="uid" value="Hello Server！"/><br/>
    <br/><input type="button" value="发送消息" onclick="send(this.form.uid.value)"/>
    <hr color="black"/>
    <h3>服务端返回的应答消息</h3>
    <textarea id="responseText" style="width:900px;height:300px;"></textarea>
</form>
</body>
</html>
```

启动springboot项目测试：

![](https://secure2.wostatic.cn/static/rsWSLu9BoiAxDH2NcefATr/image.png?auth_key=1678349815-8TS9gqUSJvgMC1sxHCFYRa-0-3832c4f78d5c5897c6101e518a4c387d)

![](https://secure2.wostatic.cn/static/2Ek6BNvcjrtuZXkmutrq5a/image.png?auth_key=1678349815-eMczAiZ33eJhdQ3UnZApqf-0-bb9af55531731383507726467927d9cb)

#### Socket

普通的长连接也是需要自定义协议的，这里我内嵌了一个自己的协议，也就是博客里面写的那一套

配置如下：

![](https://secure2.wostatic.cn/static/in9aEUVF4u2jhyH58LwByk/image.png?auth_key=1678349815-8wg5SAysvA9LW9rr4azskr-0-eb72093cbfecfed757c6dc4daffab874)

数据处理类自己编写，如同netty一样，只需要打上 **@NettyServerHandler** 注解即可，如：

```Java
@NettyServerHandler
public class MyConfigHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(MyConfigHandler.class);


    // 读取信息调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 和NIO一样有缓冲区 ByteBuf就是对ByteBuffer做了一层封装
        NettyBody msg1 = (NettyBody) msg;

        // 读取数据
        log.info("客户端信息：" + JSON.toJSON(msg1));
    }

    // 连接事件 连接成功调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        log.info(socketAddress + " 已连接");

        // 发送数据
        ctx.writeAndFlush(new NettyMsg(ServiceCodeEnum.TEST_TYPE,"Hello Client!"));
    }

    // 断开连接调用
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info(ctx.channel().remoteAddress() + " 已断开连接");
    }

    // 读取信息完成事件  信息读取完成后调用
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

    }

    // 异常处理  发生异常调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("全局异常信息 ex={}", cause.getMessage(), cause);
        ctx.close();
    }
}
```

启动springboot项目测试：

![](https://secure2.wostatic.cn/static/8D84kELJh5JjuFh6LrhdqQ/image.png?auth_key=1678349814-8CgtuBCJnRhtmZZCPneUJ8-0-4be5e31044d32ef0d3946105204de7b9)

#### 自定义

内置的协议就以上三种，最后一个就是默认的自定义，这个只会加入被注解标识的Handler，无任何内置的编解码或者Handler

![](https://secure2.wostatic.cn/static/2dM7gt1trj8URqiLEYuRgN/image.png?auth_key=1678349814-grdAJYTvqhS4s8YaYLnSCP-0-df5ed87d50d1387de2bdf63857fbbff9)



### 心跳机制

http协议是不需要心跳的，webSocket和socket两种都已经内嵌了心跳的处理了，我们只需要配置一下，然后监听心跳事件就可以了

#### 配置

此配置代表5s触发一次读事件的空闲检测

![](https://secure2.wostatic.cn/static/mzQTwQD4N1q8jmiGBnrrq3/image.png?auth_key=1678349814-7Su1J87DarAD5GuZ6jUHfX-0-75ca5e318a494646a47b42daa4a01ad6)

#### 事件

这里将Netty的空间检测事件转化为了spring中的事件机制，所以我们需要监听**NettyServerIdleEvent**事件，并将这个Listener注入到spring容器中,如下：

```Java
@Component
public class NettyServerHeartTestListener {
    private static final Logger log = LoggerFactory.getLogger(NettyServerHeartTestListener.class);

    public NettyServerHeartTestListener() {
    }

    @EventListener({NettyServerIdleEvent.class})
    public void serverIdleHandler(NettyServerIdleEvent event) {
        NettyServerNettyIdleEntity source = (NettyServerNettyIdleEntity) event.getSource();
        // 获取心跳配置
        NettyServerHeartConfig serverHeartConfig = source.getServerHeartConfig();
        // 获取netty管道 上下文
        ChannelHandlerContext ctx = source.getCtx();
        // 获取触发的事件
        IdleState state = source.getEvt().state();
        log.info("心跳事件触发：" + source.getEvt().state());
    }
}
```

#### 结果

可以看到在没消息接收的时候，每5s就会触发一次

![](https://secure2.wostatic.cn/static/hGudKFMJW4DVudo81dhzrf/image.png?auth_key=1678348647-usPqvK97WtnKo8L4xVCYGB-0-6c43586b4c02bb8a00a491be8ee2599c)

### 数据处理编排

我们知道Netty中数据处理类都是被放入管道中的，可以多个，类似责任链模式，所以我们自己的数据处理类怎么编排呢？主要靠 **@NettyServerHandler**注解，里面有个**order**字段设置排序，被该注解标记的Handler最终都会被收集，并根据Order字段来排序，依次加入到Netty的管道中，order越小，越先加入

### 如何自定义

我们看一下一个常规的Netty服务端启动，大致可分为三个部分：

1. 启动配置
2. 数据处理器设置
3. 管道处理设置

![](https://secure2.wostatic.cn/static/x2G2wWCcovhXesio6LxZBs/image.png?auth_key=1678347808-jEFZhvNBrF31YtgB5DA2YB-0-ffba0634952c22fea3bf8c6c13412a46)

**关于第一个部分现在只做了简单的配置：端口、线程数，option的配置还没做**

**关于第二个部分，数据处理器，我们是可以直接将这块替换的**

继承 **NettyServerHandlerInitializer**类，并注入到容器中即可替换,如下：

```Java
@Component
public class TestInit extends NettyServerHandlerInitializer {


    // nettyServerConfig        服务端的配置类 
    // context                  Spring容器上下文
    // nettyServerChannelInit   内置的一些协议初始化方法
    public TestInit(NettyServerConfig nettyServerConfig, ApplicationContext context, NettyServerChannelInit nettyServerChannelInit) {
        super(nettyServerConfig, context, nettyServerChannelInit);
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
//        super.initChannel(channel);
        // 这里就可以做自己的处理
    }
}
```

**关于第三个部分，管道处理类的设置，现在是有内置的一些协议，当然也是支持完成自定义的**

实现**NettyServerChannelInit**接口，并注入到容器中就可以了，如下：

**NettyServerChannelInit**接口有两个方法：

- **addChannelHandlers**：默认实现的，该方法就是获取容器中被**NettyServerHandler**注解标记的所有Handler并按照排序加入Channel中
- **initChannelHandlers**：这个就是需要自己实现的，可以调用**addChannelHandlers**方法，也可以完全自己添加

```Java
@Component
public class TestInit implements NettyServerChannelInit {


    @Override
    public void initChannelHandlers(Channel channel, NettyServerConfig nettyServerConfig, ApplicationContext context) {
        
    }
}
```

### 总结

可以看到关于数据处理方面，有三个方向可以拓展自定义的：

1. 配置**coding-type: default**
2. 替换**ChannelInitializer**
3. 自定义Channel的管道处理

## 客户端

客户端的使用配置基本是复刻的服务端，就是在协议方面，没有Http和Websocket了，只有内置的一个自定义协议，同样的只需要配置就能直接启动

![](https://secure2.wostatic.cn/static/4T6GKGvrdxM87f2LxfUgsZ/image.png?auth_key=1678348234-nveX1DPrQXaNH5VUEeo5Di-0-32a39ff9e5ce69fe8d74c2a42e7732ac)

**与服务端不同的点：**

- 注解方面：客户端为 **@NettyClientHandler**
- 数据处理器自定义：客户端为**NettyClientHandlerInitializer**
- 管道数据处理自定义：客户端为**NettyClientChannelInit**
- 心跳检测事件：客户端为**NettyClientIdleEvent**

### 断线重连机制

由于是客户端断线重连是必不可少的，所以内置了断线重连机制（也就是博客中说的那种）

配置如下（有默认值不配也行）：

![](https://secure2.wostatic.cn/static/7PtbxpufSktQseekmU2Z8f/image.png?auth_key=1678349593-vTJXjJy96DUVkmegByf4uB-0-d90f1d899f3ec576f583101acab27498)

**看看效果：**

启动连接失败，重连：

![](https://secure2.wostatic.cn/static/cSYWGimy3ERCAR29fhfQcq/image.png?auth_key=1678349695-hfAsdhru51KPZSQ17MJf3i-0-e3026dd5969d3ecc1eb442957bba1aac)

中途断线重连：

![](https://secure2.wostatic.cn/static/qSdYsK1JgXCvbq9tVB6mFm/image.png?auth_key=1678349653-iV3NTEpseQXASDyWiUZejv-0-a78aa5cad36f4e871da5749ab053d7b2)

# 二、后续

目前这只是个初版，为了增加Netty移植性以及简化Netty在Springboot项目中的操作，肯定有很多欠缺的地方（说不定还有bug），望大佬们多多建议，后续可能会加入以下功能：

- **内置几种加解密手段**
- **Option配置方面的完善**
- **增加黑白名单校验**
- **增加多种主流协议的支持**
- **增加集群方面的支持和拓展**
- **内置多几种管道数据处理器，如文件传输、图片传输等**

