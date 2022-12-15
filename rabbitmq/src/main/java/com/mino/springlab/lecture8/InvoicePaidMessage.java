package com.mino.springlab.lecture8;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoicePaidMessage {

    private String invoiceNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate paidDate;

    private String paidNumber;



}
