package pl.filmveeb.dto;

import pl.filmveeb.model.Rating;

public class RatingDto {

    private Long id;
    private String stars;

    public static RatingDto apply(Rating rating) {
        RatingDto ratingDto = new RatingDto();
        ratingDto.setId(rating.getId());
        ratingDto.setStars(String.valueOf(rating.getStars()));
        return ratingDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }
}
