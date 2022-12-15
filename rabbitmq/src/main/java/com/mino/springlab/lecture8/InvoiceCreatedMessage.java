package com.mino.springlab.lecture8;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InvoiceCreatedMessage {

    private double amount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    private String currency;

    private String invoiceNumber;
}
