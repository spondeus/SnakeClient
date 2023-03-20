package pentasnake.client.socket;


import com.badlogic.gdx.ApplicationAdapter;
import io.socket.client.IO;
import io.socket.client.Socket;
import lombok.val;

import java.net.URISyntaxException;

public class Test extends ApplicationAdapter{

    private Socket socket;

    @Override
    public void create(){
        IO.Options options = new IO.Options();
        options.transports = new String[]{"websocket"};

        try
        {
            socket = IO.socket("http://localhost:8081", options);
            socket.on(Socket.EVENT_CONNECT, args -> System.out.println("Connected"))
                    .on("message", args -> {
                        val message = (String) args[0];
                        System.out.println("Message: " + message);
                    })
                    .on(Socket.EVENT_DISCONNECT, args -> System.out.println("Disconnected"));
        }catch (URISyntaxException e){
            throw new RuntimeException(e);
        }
        socket.connect();
        socket.emit("message", "hallo");
    }
}

