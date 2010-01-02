class StartUpBean
  FILE_PATH = "./res/yaml/start_up.yaml"
  attr_accessor :rect, :sashes, :files, :proj_def #array[x,y,w,h], array, array, string
  
  def initialize(rect, sashes, files, proj_def)
    @rect, @sashes, @files, @proj_def = rect, sashes, files, proj_def
  end
  
end