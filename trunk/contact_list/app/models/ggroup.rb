class Ggroup < ActiveRecord::Base
  has_many :staffs
  has_and_belongs_to_many :grants
end
