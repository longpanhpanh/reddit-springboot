package project.spring.redditspring.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import project.spring.redditspring.dto.SubredditDto;
import project.spring.redditspring.model.Post;
import project.spring.redditspring.model.Subreddit;
import project.spring.redditspring.service.AuthService;

import java.util.List;


@Mapper(componentModel = "spring")
public interface SubredditMapper {




    @Mapping(target = "postCount", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapSubredditToDto(Subreddit subreddit);


    default Integer mapPosts(List<Post> postCount) {
        return postCount.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "posts", ignore = true)
    Subreddit mapDtoToSubreddit(SubredditDto subredditDto);
}
