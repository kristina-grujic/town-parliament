class User < ApplicationRecord
  # Include default devise modules. Others available are:
  # :confirmable, :lockable, :timeoutable and :omniauthable
  devise :database_authenticatable, :registerable,
         :recoverable, :rememberable, :trackable, :validatable

  has_and_belongs_to_many :roles

  # will citizen role be a default one?
  #   how will we add other roles to users?
  #   Should admin add those roles?
  after_create :make_citizen


  def role?(role)
    return !!self.roles.find_by_name(role.to_s)
  end

  # CITIZEN

  def make_citizen
    self.roles << Role.citizen
  end

  # ALDERMAN

  def make_alderman
    revoke_citizen
    self.roles << Role.alderman
  end

  def revoke_alderman
    self.roles.delete(Role.alderman)
  end

  def alderman?
    role?(:alderman)
  end

  # ASSEMBLY PRESIDENT

  def make_assembly_president
    revoke_citizen
    self.roles << Role.assembly_president
  end

  def revoke_assembly_president
    self.roles.delete(Role.assembly_president)
  end

  def assembly_president?
    role?(:assembly_president)
  end



end
