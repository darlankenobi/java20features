package recordpatterns;

sealed interface Card permits PicPayCard, PicPayBenefits {
    String bin();

}
