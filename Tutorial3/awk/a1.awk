function startToSTART () {
    gsub(/start/,"START");
    print $0 > FILENAME;
}

{
    startToSTART();
}



