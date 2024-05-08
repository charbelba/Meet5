package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {


		SpringApplication.run(DemoApplication.class, args);
		//System.out.println(lengthOfLongestSubstring("dvdf"));
		System.out.println(checkInclusion("ab", "eidbaooo"));
		System.out.println(0);
	}
	public static int lengthOfLongestSubstring(String s) {
		List<Character> substringL = new ArrayList<>();
		int largestlength = 0;
		for(int right = 0; right < s.length(); right++) {
			if(substringL.contains(s.charAt(right))) {
				// get the index of the char
				int index = substringL.indexOf(s.charAt(right));

				substringL.remove(index);

				if(index > 0) {

					substringL.subList(0, index).clear();

				}			}
			substringL.add(s.charAt(right));
			largestlength = Math.max(largestlength, substringL.size());
		}

		return largestlength;
	}

	public static boolean checkInclusion(String s1, String s2) {
		int n = s1.length();
		int[] freq = new int[26];
		int m = s2.length();
		for (int i = 0; i < n; i++) {
			freq[s1.charAt(i) - 'a']++;
		}
		int[] freq2 = new int[26];
		for (int i = 0; i < m; i++) {
			freq2[s2.charAt(i) - 'a']++;
			if (i >= n) {
				System.out.println(s2.charAt(i));
				freq2[s2.charAt(i - n) - 'a']--;
			}
			if (Arrays.equals(freq, freq2))
				for (int i1 : freq) {
					System.out.print(i1);
				}
			for (int i1 : freq2) {
				System.out.print(i1);
			}
			return true;
		}
		return false;
	}

	public static boolean searchMatrix(int[][] matrix, int target) {
		int rows = matrix.length;
		if (rows == 0) return false;
		int cols = matrix[0].length;

		int left = 0, right = rows * cols - 1;

		while (left <= right) {
			int mid = left + (right - left) / 2;
			int midValue = matrix[mid / cols][mid % cols];

			if (midValue == target) {
				return true;
			} else if (midValue < target) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}

		return false;
	}

}
