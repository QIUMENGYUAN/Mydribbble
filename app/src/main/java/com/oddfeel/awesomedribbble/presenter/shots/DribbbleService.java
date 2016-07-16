
package com.oddfeel.awesomedribbble.presenter.shots;

/**
 * Dribbble API - http://developer.dribbble.com/v1/
 */
public interface DribbbleService {

    String ENDPOINT = "https://api.dribbble.com/";
    String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss Z";
    int PER_PAGE_MAX = 100;
    int PER_PAGE_DEFAULT = 30;


    /* Shots */

   /* @GET("v1/shots")
    Call<List<Shot>> getPopular(@Query("page") Integer page,
                                @Query("per_page") Integer pageSize);

    @GET("v1/shots?sort=recent")
    Call<List<Shot>> getRecent(@Query("page") Integer page,
                               @Query("per_page") Integer pageSize);

    @GET("v1/shots?list=debuts")
    Call<List<Shot>> getDebuts(@Query("page") Integer page,
                               @Query("per_page") Integer pageSize);

    @GET("v1/shots?list=animated")
    Call<List<Shot>> getAnimated(@Query("page") Integer page,
                                 @Query("per_page") Integer pageSize);

    @GET("v1/shots")
    Call<List<Shot>> getShots(@Query("list") @ShotType String shotType,
                              @Query("timeframe") @ShotTimeframe String timeframe,
                              @Query("sort") @ShotSort String shotSort);

    @GET("v1/shots/{id}")
    Call<Shot> getShot(@Path("id") long shotId);

    @GET("v1/user/following/shots")
    Call<List<Shot>> getFollowing(@Query("page") Integer page,
                                  @Query("per_page") Integer pageSize);

    *//* List the authenticated user’s shot likes *//*
    @GET("v1/user/likes")
    Call<List<Like>> getUserLikes(@Query("page") Integer page,
                                  @Query("per_page") Integer pageSize);

    *//* List the authenticated user’s shots *//*
    @GET("v1/user/shots")
    Call<List<Shot>> getUserShots(@Query("page") Integer page,
                                  @Query("per_page") Integer pageSize);

    *//* Shot likes *//*

    @GET("v1/shots/{id}/likes")
    Call<List<Like>> getShotLikes(@Path("id") long shotId,
                                  @Query("page") Integer page,
                                  @Query("per_page") Integer pageSize);

    @GET("v1/shots/{id}/like")
    Call<Like> liked(@Path("id") long shotId);

    @POST("v1/shots/{id}/like")
    Call<Like> like(@Path("id") long shotId);

    @DELETE("v1/shots/{id}/like")
    Call<Void> unlike(@Path("id") long shotId);


    *//* Comments *//*

    @GET("v1/shots/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") long shotId,
                                    @Query("page") Integer page,
                                    @Query("per_page") Integer pageSize);

    @GET("v1/shots/{shot}/comments/{id}/likes")
    Call<List<Like>> getCommentLikes(@Path("shot") long shotId,
                                     @Path("id") long commentId);

    @POST("v1/shots/{shot}/comments")
    Call<Comment> postComment(@Path("shot") long shotId,
                              @Query("body") String body);


    @DELETE("v1/shots/{shot}/comments/{id}")
    Call<Void> deleteComment(@Path("shot") long shotId,
                             @Path("id") long commentId);

    @GET("v1/shots/{shot}/comments/{id}/like")
    Call<Like> likedComment(@Path("shot") long shotId,
                            @Path("id") long commentId);

    @POST("v1/shots/{shot}/comments/{id}/like")
    Call<Like> likeComment(@Path("shot") long shotId,
                           @Path("id") long commentId);

    @DELETE("v1/shots/{shot}/comments/{id}/like")
    Call<Void> unlikeComment(@Path("shot") long shotId,
                             @Path("id") long commentId);


    *//* Users *//*

    @GET("v1/users/{user}")
    Call<User> getUser(@Path("user") long userId);

    @GET("v1/users/{user}")
    Call<User> getUser(@Path("user") String username);

    @GET("v1/user")
    Call<User> getAuthenticatedUser();

    @GET("v1/users/{user}/shots")
    Call<List<Shot>> getUsersShots(@Path("user") long userId,
                                   @Query("page") Integer page,
                                   @Query("per_page") Integer pageSize);

    @GET("v1/users/{user}/shots")
    Call<List<Shot>> getUsersShots(@Path("user") String username,
                                   @Query("page") Integer page,
                                   @Query("per_page") Integer pageSize);

    @GET("v1/user/following/{user}")
    Call<Void> following(@Path("user") long userId);

    @GET("v1/user/following/{user}")
    Call<Void> following(@Path("user") String username);

    @PUT("v1/users/{user}/follow")
    Call<Void> follow(@Path("user") long userId);

    @PUT("v1/users/{user}/follow")
    Call<Void> follow(@Path("user") String username);

    @DELETE("v1/users/{user}/follow")
    Call<Void> unfollow(@Path("user") long userId);

    @DELETE("v1/users/{user}/follow")
    Call<Void> unfollow(@Path("user") String username);

    @GET("v1/users/{user}/followers")
    Call<List<Follow>> getUserFollowers(@Path("user") long userId,
                                        @Query("page") Integer page,
                                        @Query("per_page") Integer pageSize);


    *//* Teams *//*

    @GET("v1/teams/{team}/shots")
    Call<List<Shot>> getTeamShots(@Path("team") long teamId,
                                  @Query("page") Integer page,
                                  @Query("per_page") Integer pageSize);


    *//* Magic Constants *//*

    String SHOT_TYPE_ANIMATED = "animated";
    String SHOT_TYPE_ATTACHMENTS = "attachments";
    String SHOT_TYPE_DEBUTS = "debuts";
    String SHOT_TYPE_PLAYOFFS = "playoffs";
    String SHOT_TYPE_REBOUNDS = "rebounds";
    String SHOT_TYPE_TEAMS = "teams";
    String SHOT_TIMEFRAME_WEEK = "week";
    String SHOT_TIMEFRAME_MONTH = "month";
    String SHOT_TIMEFRAME_YEAR = "year";
    String SHOT_TIMEFRAME_EVER = "ever";
    String SHOT_SORT_COMMENTS = "comments";
    String SHOT_SORT_RECENT = "recent";
    String SHOT_SORT_VIEWS = "views";

    // Shot type
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            SHOT_TYPE_ANIMATED,
            SHOT_TYPE_ATTACHMENTS,
            SHOT_TYPE_DEBUTS,
            SHOT_TYPE_PLAYOFFS,
            SHOT_TYPE_REBOUNDS,
            SHOT_TYPE_TEAMS
    })
    @interface ShotType {}

    // Shot timeframe
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            SHOT_TIMEFRAME_WEEK,
            SHOT_TIMEFRAME_MONTH,
            SHOT_TIMEFRAME_YEAR,
            SHOT_TIMEFRAME_EVER
    })
    @interface ShotTimeframe {}

    // Short sort order
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            SHOT_SORT_COMMENTS,
            SHOT_SORT_RECENT,
            SHOT_SORT_VIEWS
    })
    @interface ShotSort {}
*/
}
