package recordpatterns;

public class RecordPatternsForInstanceOf {

    static void showCard(Card card) {
        System.out.println("-------------------");
        if(card instanceof PicPayCard) {
            var picpayCard = (PicPayCard) card;
            System.out.println(String.format("Is picpay Card with name %s and with dark grey color ", picpayCard.nickname()));
            System.out.println(String.format("Bandeira: %s", picpayCard.brand().name()));
        }

        if(card instanceof PicPayBenefits) {
            var picpayCardBenefits = (PicPayBenefits) card;
            System.out.println(String.format("Is picpay Card Benefits with name %s and with black color ", picpayCardBenefits.nickname()));
            System.out.println(String.format("Bandeira: %s", picpayCardBenefits.brand().name()));
        }
    }

    static void showCardWithRecordPattern(Card card) {
        System.out.println("-------------------");
        if(card instanceof PicPayCard(String name, String status, Boolean isCredit, Brand(String id, String brandName, String image))) {
            System.out.println(String.format("Is picpay Card with name %s and with dark grey color ", name));
            System.out.println(isCredit ? "Multiple" : "Debit");
            System.out.println(String.format("Bandeira: %s".formatted(brandName)));
        }

        if(card instanceof PicPayBenefits(var name, var nextRecharge, Brand(var id, var brandName, var image))) {
            System.out.println(String.format("Is picpay Card Benefits with name %s and with black color ", name));
            System.out.println(String.format("Data da próxima recarga: %s", nextRecharge));
            System.out.println(String.format("Bandeira: %s".formatted(brandName)));
        }
    }

    public static void main(String[] args) {
        final var picpayCard = new PicPayCard("Meu PicPay Card", "Fechada", true, new Brand("7", "picpay", "picpay"));
        final var picpayBenefitsCard = new PicPayBenefits("Meu Cartão de Benefícios", "28/04/2023", new Brand("5", "elo", "elo"));
        showCard(picpayCard);
        showCard(picpayBenefitsCard);
        System.out.println("-----------------------");
        showCardWithRecordPattern(picpayCard);
        showCardWithRecordPattern(picpayBenefitsCard);
    }

}
