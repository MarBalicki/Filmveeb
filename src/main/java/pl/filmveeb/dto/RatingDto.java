package pl.filmveeb.dto;

import pl.filmveeb.model.Rating;

public class RatingDto {

    private Long id;
    private String ratingValue;
    private String ratingPlName;

    public static RatingDto apply(Rating rating) {
        RatingDto ratingDto = new RatingDto();
        ratingDto.setId(rating.getId());
        ratingDto.setRatingValue(rating.getRatingValue().getPlName());
        ratingDto.setRatingPlName(rating.getRatingValue().getPlName());
        return ratingDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(String ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getRatingPlName() {
        return ratingPlName;
    }

    public void setRatingPlName(String ratingPlName) {
        this.ratingPlName = ratingPlName;
    }
}
