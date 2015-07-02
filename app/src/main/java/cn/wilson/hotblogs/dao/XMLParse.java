package cn.wilson.hotblogs.dao;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

import cn.wilson.hotblogs.bean.authorBean;
import cn.wilson.hotblogs.bean.blogBean;

/**
 * Created by KingFlyer on 2015/6/30.
 */
public class XMLParse {
    public static ArrayList<blogBean> getInfos(XmlPullParser parser) {
        ArrayList<blogBean> blogs = null;
        blogBean blog = null;
        authorBean author = null;
        int type = 0;
        try {
            type = parser.getEventType();
            while (type != XmlPullParser.END_DOCUMENT) {
                switch (type) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:
                        String name = parser.getName();
                        if ("feed".equals(name)) {
                            blogs = new ArrayList<blogBean>();
                        } else if ("entry".equals(name)) {
                            blog = new blogBean();
                        } else if ("id".equals(name)) {
                            if (blog != null)
                                blog.setId(parser.nextText());
                        } else if ("title".equals(name)) {
                            if (blog != null)

                                blog.setTitle(parser.nextText());
                        } else if ("summary".equals(name)) {
                            if (blog != null)

                                blog.setSummary(parser.nextText());
                        } else if ("published".equals(name)) {
                            if (blog != null)

                                blog.setPublishedtime(parser.nextText());
                        } else if ("updated".equals(name)) {
                            if (blog != null)

                                blog.setUpdatedtime(parser.nextText());
                        } else if ("author".equals(name)) {
                            author = new authorBean();
                        } else if ("link".equals(name)) {
                            if (blog != null) {
                                blog.setUrl(parser.getAttributeValue(1));
                            }
                            parser.nextText();
                        } else if ("blogapp".equals(name)) {
                            if (blog != null)

                                blog.setBlogapp(parser.nextText());
                        } else if ("diggs".equals(name)) {
                            if (blog != null)

                                blog.setDiggs(Integer.valueOf(parser.nextText()));
                        } else if ("views".equals(name)) {
                            if (blog != null)

                                blog.setViews(Integer.valueOf(parser.nextText()));
                        } else if ("comments".equals(name)) {
                            if (blog != null)

                                blog.setComments(Integer.valueOf(parser.nextText()));
                        } else if ("name".equals(name)) {
                            if (author != null)
                                author.setName(parser.nextText());

                        } else if ("avatar".equals(name)) {
                            if (author != null)
                                author.setAvatar(parser.nextText());

                        } else if ("uri".equals(name)) {
                            if (author != null)
                                author.setUri(parser.nextText());

                        } else if ("topic".equals(name)) {
                            if (blog != null)

                                blog.setTopic(parser.nextText());
                        } else if ("topicIcon".equals(name)) {
                            if (blog != null)

                                blog.setTopicIcon(parser.nextText());
                        } else if ("sourceName".equals(name)) {
                            if (blog != null)
                                blog.setSourceName(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("entry".equals(parser.getName())) {
                            blogs.add(blog);
                            blog = null;
                        }
                        if ("author".equals(parser.getName())) {
                            if (blog != null && author != null) {
                                blog.setAuthor(author);
                            }

                        }
                        break;
                }
                type = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blogs;
    }


    /**
     * 得到博客详细内容
     *
     * @param parser 解析器
     * @return
     */
    public static String getBlogContent(XmlPullParser parser) {
        String content = "";
        int type = 0;
        try {
            type = parser.getEventType();
            while (type != XmlPullParser.END_DOCUMENT) {
                switch (type) {
                    case XmlPullParser.START_TAG:
                        if ("string".equals(parser.getName())) {
                            content = parser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                type = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }
}
