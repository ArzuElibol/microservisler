package com.example.productservice.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class InternalApiResponse<T> { //burada <T> yi ekleyince artık classımız generic bir class oldu. Generic class type parametresi alırlar. bu bi string, integer veya class bile olabilir. Rest APi ımız için tek tipte bir Response yapısı oluşturmuş olduk.

    private FriendlyMessage friendlyMessage;
    private T payload; //payload alanına response vereceğz. response da her controller için değişebileceği için T diye generic type tanımladık ve istediğimiz türde data verebiliriz. Burda classımızı gneric oluşturduğumuz için generic bir field tanımlayabildik
    private boolean hasError;
    private List<String> errorMessages;
    private HttpStatus httpStatus;
}
