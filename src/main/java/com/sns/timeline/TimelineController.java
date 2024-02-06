package com.sns.timeline;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sns.timeline.bo.TimelineBO;
import com.sns.timeline.domain.CardView;

import jakarta.servlet.http.HttpSession;

@Controller
public class TimelineController {

	@Autowired
	private TimelineBO timelineBO;

	@GetMapping("/timeline/timeline-view")
	public String timelineView(Model model, HttpSession session) {
		// 글 목록 조회
		// 댓글 목록 가져오기
		// List<PostEntity> postList = postBO.getPostList();
		// List<Comment> commentList = commentBO.getCommentList();

		// model.addAttribute("postList", postList);
		// model.addAttribute("commentList", commentList);

		Integer userId = (Integer)session.getAttribute("userId");
		
		List<CardView> cardViewList = timelineBO.generateCardViewList(userId);

		model.addAttribute("cardViewList", cardViewList);

		model.addAttribute("viewName", "timeline/timeline");

		return "template/layout";
	}

}