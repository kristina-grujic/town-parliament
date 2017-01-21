class Role < ApplicationRecord

  has_and_belongs_to_many :users

  def self.citizen
    find_or_create_by(:name => "citizen")
  end

  def self.alderman
    find_or_create_by(:name => "alderman")
  end

  def self.assembly_president
    find_or_create_by(:name => "assembly_president")
  end

end
