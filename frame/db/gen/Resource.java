package frame.db.gen;


import lombok.Data;
import util.constants.FileSuffixConstants;
import util.constants.Symbol;
import util.io.TextFile;

import java.io.*;
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
        String fileName = path + Symbol.SLASH + this.id + FileSuffixConstants.TXT;
        TextFile.write(fileName, this.data.toString());
    }
}