public class Problem_1
{
	public static void solution(int n, int s) 
	{ 
		if (s < 12) 
	    { 
	        for (int i=0; i < n; i++) 
	        { 
	            System.out.println("first line..."); 
	        } 
	        
	        solution(n-1, s); 
	    }  
	    else 
	    { 
	        for (int k=0; k < 3000; k++) 
	        { 
	            for (int j=0; j < n * k; j++)
	            { 
	          	  System.out.println("second line..."); 
	            } 
	        } 
	    } 
	} 
}


