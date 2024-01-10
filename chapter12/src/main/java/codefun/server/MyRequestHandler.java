package codefun.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MyRequestHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        if(msg instanceof io.netty.handler.codec.http.FullHttpRequest)
        {
            var request = (io.netty.handler.codec.http.FullHttpRequest) msg;

            var buf = Unpooled.copiedBuffer(request.toString().getBytes("UTF-8"));
            ctx.writeAndFlush(buf);

            System.out.println(request.uri());
        }
        System.out.println(msg);
    }
}
