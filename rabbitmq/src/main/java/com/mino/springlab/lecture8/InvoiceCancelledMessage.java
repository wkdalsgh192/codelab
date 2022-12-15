package com.mino.springlab.lecture8;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceCancelledMessage {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate cancelledDate;

    private String invoiceNumber;

    private String reason;
}
