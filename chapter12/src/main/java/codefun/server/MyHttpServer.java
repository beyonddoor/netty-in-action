package codefun.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.net.InetSocketAddress;

public class MyHttpServer {

    private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private final NioEventLoopGroup group = new NioEventLoopGroup();
    private Channel channel;

    public MyHttpServer() {
    }

    public void start(int port) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                .childHandler(new MyHttpServerInitializer(channelGroup));
        var channelFuture = bootstrap.bind();
        channelFuture.syncUninterruptibly();
        this.channel = channelFuture.channel();
    }

    private void shutdown() {
        channelGroup.close();
        channel.close();
        group.shutdownGracefully();
        System.out.println("Server shutdown");
    }

    public static void main(String[] args) throws InterruptedException {
        var server = new MyHttpServer();
        server.start(Integer.parseInt(args[0]));
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
    }
}
