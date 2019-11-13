use spring_blog_db;

-- seed post_details
# insert into post_details (history_of_post, is_awesome, topic_description) values
# ('A long long time ago...', true, 'movie quotes'),
# ('昔々あるところに', true, '日本語'),
# ('the history of the cats post', true, 'cats');                                                                                 ;

# insert into post_details (history_of_post, topic_description) values
# ('history of blah', 'description of blah');

-- seed users
insert into users (username, password, email) values
('gwash', 'george', 'gwash@usa.com'),
('mwash', 'martha', 'mwash@usa.com'),
('alinc', 'abraham', 'alinc@usa.com');

-- seed posts
insert into posts (title, body, user_id) values
('My First Post', 'Super cool content.', 1),
('日本語でも書けるな', 'スゲー', 2),
('A Lovely Bunch of Cat Images', 'What could be better?', 1);

-- seed post_images
# insert into post_images (image_title, url, post_id) values
# ('Vertical Cat', 'http://placekitten.com/200/300', 4),
# ('Horizontal Cat', 'http://placekitten.com/300/200', 4),
# ('Square Cat', 'http://placekitten.com/250/250', 4);

-- seed tags
# insert into tags (name) values
# ('Silly'),
# ('Funny'),
# ('Humor');

-- seed post_tag
# insert into post_tag (post_id, tag_id) values
# (1, 1),
# (1, 2),
# (4, 3),
# (4, 2);

