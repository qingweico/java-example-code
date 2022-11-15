package frame.db.gen;


import lombok.Data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@Data
class Resource<T> {
    int id;
    T data;
    String uri;
    CompletableFuture<HttpResponse<String>> future;


    public Resource(String uri, int id) {
        this.uri = uri;
        this.id = id;
    }

    public URI getUri() {
        try {
            return new URI(this.uri);
        } catch (URISyntaxException e) {
            // swallow the exception
        }
        return null;
    }


    public void save(String path) throws IOException {
        var file = new File(path + this.id + ".txt");
        try (var objectOutStream = new ObjectOutputStream(new FileOutputStream(file))) {
            System.out.println(data);
            objectOutStream.writeObject(this.data);
            objectOutStream.flush();
        }
    }
}