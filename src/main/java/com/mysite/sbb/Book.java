package com.mysite.sbb;
import lombok.*;

@Getter
@RequiredArgsConstructor
public class Book {
	private final String title;
	private final String author;
	
	
	
	public static void main(String[] args) {
		Book bk = new Book("a","b");
		System.out.println("타이틀:"+bk.getTitle());
		System.out.println("작가:"+bk.getAuthor());
	}

		
}


