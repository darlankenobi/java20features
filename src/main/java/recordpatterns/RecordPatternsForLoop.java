package recordpatterns;

import java.util.List;

public class RecordPatternsForLoop {


    public static void main(String[] args) {
        final var picpayCardMultiple = new PicPayCard(
            "Meu PicPay Card",
            "Fechada",
            true,
            new Brand("7", "picpay", "picpay"));
        final var picpayCardDebit = new PicPayCard(
            "Meu PicPay Card",
            "Sem fatura",
            false,
            new Brand("7", "picpay", "picpay"));
        final var cards = List.of(picpayCardMultiple, picpayCardDebit);

        for(PicPayCard card: cards) {
            System.out.printf(
                "Cartão %s com status %s e bandeira %s%n",
                card.nickname(),
                card.invoiceStatus(),
                card.brand().name());
        }
        System.out.println("----------------------");
        for(PicPayCard(var name, var status, var isMultiple, Brand(var id, var brandName, var image)): cards) {
            System.out.printf(
                "Cartão %s com status %s e bandeira %s%n",
                name,
                status,
                brandName);
        }
    }

}
