include Comparable

class SearchViewHistory
  SEARCH = "Search"
  VIEW_FILE = "File"
  PATH = 'history.yaml'
    
  attr_accessor :file_path, :intfc_id, :look_in, :rate, :rs_rq, :hi_type
  
  def initialize(hi_type, intfc_id="", rs_rq="", look_in="", file_path="", rate=0)
    @hi_type = hi_type
    @intfc_id = intfc_id
    @rs_rq = rs_rq
    @look_in = look_in
    @file_path = file_path
    @rate = rate
  end
  
  def ==(other)
    return false unless other.is_a?(SearchViewHistory)
    return false if self.hi_type != other.hi_type
    return true if self.hi_type == SearchViewHistory::SEARCH && self.intfc_id == other.intfc_id && self.look_in == other.look_in && self.rs_rq == other.rs_rq
    return true if self.hi_type == SearchViewHistory::VIEW_FILE && self.file_path == other.file_path
    return false
  end
  
  def <=>(other)
    return nil unless other.is_a?(SearchViewHistory)
    return other.rate <=> self.rate 
  end
  
  def to_s
    if self.hi_type == SearchViewHistory::SEARCH
      "#{self.hi_type}: #{self.intfc_id}, #{self.rs_rq}, #{self.look_in}"
    else
      "#{self.hi_type}: #{self.file_path}"
    end
  end
end