class ProjectBean
  attr_accessor :name, :path
  
  def initialize(name, path)
    @name, @path = name, path
  end
  
  def ==(other)
    return false unless other.is_a?(ProjectBean)
    return true if self.name == other.name
    return false
  end
end