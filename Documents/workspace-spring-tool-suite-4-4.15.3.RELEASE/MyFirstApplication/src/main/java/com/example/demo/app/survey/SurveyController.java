package com.example.demo.app.survey;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/survey")
public class SurveyController {
	
	//URLでアクセスする場合
	@GetMapping("/form")
	public String form(SurveyForm surveyForm,
			Model model,
			@ModelAttribute("complete") String complete) { //フラッシュスコープの値をHtmlでレンダリングできる。フラッシュスコープを利用する場合は、＠を使用する。設定したキーも追加する。
		model.addAttribute("title","Survey Form");
		return "survey/form";
	}
	
	//戻るボタンで戻てくる場合
	@PostMapping("/form")
	public String formGoBack(SurveyForm surveyForm, Model model) {
		model.addAttribute("title","Survey Form");
		return "survey/form";
	}
	
	@PostMapping("/confirm")
	public String confirm(@Validated SurveyForm surveyForm,
			BindingResult result, /*バリデーションをかけた後の結果が返ってくる*/
			Model model) {
		if(result.hasErrors()) {
			model.addAttribute("title","Survey Form");
			return "survey/form";
		}
		model.addAttribute("title", "Confirm Page");
		return "survey/confirm";
	}
	
	@PostMapping("/complete")
	public String complete(@Validated SurveyForm surveyForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			model.addAttribute("title","Survey Form");
			return "survey/form";
		}
		redirectAttributes.addFlashAttribute("complete","登録しました!");
		return "redirect:/survey/form"; //URLをさしている。クライアントに一度レスポンスを返し、クライアントから再びリクエストが返される
	}
	
}
