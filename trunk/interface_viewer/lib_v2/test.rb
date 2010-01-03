array = ["0", "1","2"]

puts array.index("2")

array.delete("1")

puts array.index("2")

#puts '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>'.length # e:132 a:128
      
#puts '<object class="wxSplitterWindow" name="sw_dir">'.length
#puts '<style>wxSP_3D|wxSP_NOBORDER</style>'.length
#puts '<sashpos>0</sashpos>'.length
    
    #size1, 5,263, 128 133+128=261+2 =263
    
    #70 + 58 = 128
    #126
    
puts '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<resource2 xmlns="http://www.wxwindows.org/wxxrc" version="2.3.0.1">'. index('<resource2 xmlns="http://www.wxwindows.org/wxxrc" version="2.3.0.1">')