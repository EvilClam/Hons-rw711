BEGIN {
    FS = "//";
    a = 0;  
}
{
   a++;  
    DELine = "";
    current = "";
    currentlength = "";
    do {
	getline current	
	if (match(substr(current,0,3),"DE")) {
    	    DELine = DELine "" current;
    	}
	
	if (match(substr(current,0,3),"MA") && match(current,"SY=") && match(current,"/M:")) {
	    split(current,temp3,"'");
	    currentlength = currentlength "" temp3[2];	     
	}
    } while (!match(current,"TAXO-RANGE") && a < 480);
    if (index(currentlength,"GGTTAA") != 0) {
	print DELine;
    }   
}
END {
}
