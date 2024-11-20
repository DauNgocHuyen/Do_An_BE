package com.daungochuyen.abc;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class Excellent implements Rating {

    @Override
    public int getRating() {
        return 1;
    }
}
