class Ggroup < ActiveRecord::Base
  DEFAULT = ':public'
  has_many :staffs
  has_and_belongs_to_many :grants
end
