class Grant < ActiveRecord::Base
  NIL='nil'
  CURRENT='current'
  has_and_belongs_to_many :ggroups
end
