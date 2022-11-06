package com.mino.springlab.lecture5;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
class Picture {
    private String name;
    private String type;
    private String source;
    private long size;

}
