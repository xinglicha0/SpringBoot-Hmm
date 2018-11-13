package com.xlc.hmm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@Component
//@Order(value=1)
//class StartUpRunner implements CommandLineRunner {  
//    @Override 
//    public void run(String... args) throws Exception {
////    	new ParamFromSql();
//    	System.out.println("SUCCESS");
//    }  
//}

@Component
class ParamFromSql {
	
	@Autowired
	private Table1Respository table1Respository;
	
	@Autowired
	private Table2Respository table2Respository;
	
	@Autowired
	private Table3Respository table3Respository;
	
	public static ParamFromSql paramFromSql;
	
	static List<String> wordlist;
	static List<String> labellist;
	static double[] pi;
    static double[][] A;
    static double[][] B;

    @PostConstruct
    public void init() {
    	
    	paramFromSql = this;
    	paramFromSql.table1Respository = this.table1Respository;
    	paramFromSql.table2Respository = this.table2Respository;
    	paramFromSql.table3Respository = this.table3Respository;
    	
    	wordlist = new ArrayList<String>();
		labellist = new ArrayList<String>();
//    	getParamFromMysql();
//    	Table1 table1 = paramFromSql.table1Respository.getOne(1);
//    	paramFromSql.table1Respository.getOne(1);
//		System.out.println(paramFromSql.table1Respository.getOne(1));
		Table1 table1 = paramFromSql.table1Respository.getOne(1);
		System.out.println(table1.getLabelList());
		labellist = Arrays.asList(table1.getLabelList().split(" "));
		wordlist = Arrays.asList(table1.getWordList().split(" "));
		String[] piStr = table1.getPi().split(" ");
		int labelSize= table1.getLabelSize();
		int wordSize = table1.getWordSize();
		pi = new double[labelSize];
		A = new double[labelSize][labelSize];
		B = new double[labelSize][wordSize];
		
		int j = 1;
		for (int i = 0; i < labelSize; ++i) {
			pi[i] = Double.valueOf(piStr[i]);
			
			String[] rowAStrs = paramFromSql.table2Respository.getOne(j).getRowA().split(" ");
			for(int k = 0; k < labelSize; ++k) {
				A[i][k] = Double.valueOf(rowAStrs[k]);
			}
			
			String[] rowBStrs = paramFromSql.table3Respository.getOne(j).getRowB().split(" ");
			for(int k = 0; k < wordSize; ++k) {
				B[i][k] = Double.valueOf(rowBStrs[k]);
			}
			
			++j;
			
		}
		
//		System.out.println(wordlist);
//		pi = new double[] {666.66, 2.9, 3.0};
		
		System.out.println("SUCCESS");
    }
    
//	public ParamFromSql(){
		
//		wordlist = new ArrayList<String>();
//		labellist = new ArrayList<String>();
//		System.out.println("调用了么");
//		getParamFromMysql();
//	}
	
//	private void getParamFromMysql() {
//		//读取数据库数据
//		Table1 table1 = paramFromSql.table1Respository.getOne(1);
//		System.out.println(table1.getLabelList());
//	}
	
}

class Test{
	public void test() {
		System.out.println("-------------------------");
		System.out.println(ParamFromSql.A[0][0]);
		System.out.println(ParamFromSql.B[0][0]);
		System.out.println("-------------------------");
	}
}


class SetLabel{

	public String setLabel(String strInput) {
		String result = "";
		try {
			int[] labelindex = viterbi(strInput, ParamFromSql.pi, ParamFromSql.A, ParamFromSql.B);
			String[] strwords = strInput.split(" ");
			for (int i = 0; i < labelindex.length; i++) {
				result += strwords[i] + "/" + ParamFromSql.labellist.get(labelindex[i]) + " ";
				
			}	
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// viterbi
	public int[] viterbi(String string, double[] pi, double[][] A, double[][] B) throws IOException{
		
		//
		//System.out.println(wordlist.indexOf("前"));
		//System.out.println(B[0][367]);
		
		String[] words = string.split(" ");
		double[][] delta = new double[words.length][pi.length];
		int[][] way = new int[words.length][pi.length];
		int[] labelindex = new int[words.length];
        //System.out.println(words[0]);
		for (int i = 0; i < pi.length; i++) {
			delta[0][i] = pi[i] * B[i][ParamFromSql.wordlist.indexOf(words[0])];  //////////////////////////////////////////////
		}
		for (int t = 1; t < words.length; t++) {
			//System.out.println(words[t]);
			for (int i = 0; i < pi.length; i++) {
				for (int j = 0; j < pi.length; j++) {
					////////
					//System.out.println("t:" +t + "i:" + i + "j:" + j + "wordlist.indexOf(words[t]):"
						//	+ wordlist.indexOf(words[t]));
					if(delta[t][i] < delta[t-1][j] * A[j][i] * B[i][ParamFromSql.wordlist.indexOf(words[t])]) {
						delta[t][i] = delta[t-1][j] * A[j][i] * B[i][ParamFromSql.wordlist.indexOf(words[t])];
						way[t][i] = j;
					}
				}
			}
		}
		double max = delta[words.length - 1][0];
		labelindex[words.length - 1] = 0;
		for (int i = 0; i < pi.length; i++) {
			if (delta[words.length - 1][i] > max) {
				max = delta[words.length - 1][i];
				labelindex[words.length - 1] = i;
			}
		}
		for (int t = words.length - 2; t >= 0; t--) {
			labelindex[t] = way[t + 1][labelindex[t + 1]];
		}
		//System.out.println(Arrays.toString(labelindex));
		return labelindex;
	}
	
}

@RestController
public class HmmController {
	
//	@Autowired
//	private Table1Respository table1Respository;
	
	
	public String justDoIt(String str) {
		String resultStr = null;
		try {
			resultStr =  new SetLabel().setLabel(str);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return resultStr;
	}

	@PostMapping("/hmm")
	public String hmmDemo(@RequestParam(value = "str", required = false, defaultValue = "0") String testStr) {
//		String str = "我 是 中科院 研究生";
//		return testStr;
		if(testStr.equals("0")) {
			return "are you kidding me?";
		}else {
			return justDoIt(testStr);
		}
		
	}
	
	@GetMapping("/test")
	public String test() {
//		new Test().test();
		return "do you like me?";
	}

}





