class ProjectsController < ApplicationController
  layout 'board_content'
  # GET /projects
  # GET /projects.xml
  def index
    @order_by = params[:order_by] || 'project_name'
    @projects = Project.paginate :page=>params[:page], :per_page=>12, :order=>@order_by, :conditions=>"project_name != '#{Project::DEFAULT}'"

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @projects }
    end
  end

  #GET /project_list
  def project_list
    @projects = Project.all :conditions=>"project_name != '#{Project::DEFAULT}'"
    respond_to do |format|
      format.js
    end
  end

  #GET /projects/groups
  def groups
    @groups = Project.find(params[:id]).groups
    respond_to do |format|
      format.js
    end
  end

  # GET /projects/1
  # GET /projects/1.xml
  def show
    @project = Project.find(params[:ids])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @project }
    end
  end

  # GET /projects/new
  # GET /projects/new.xml
  def new
    @project = Project.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @project }
    end
  end

  # GET /projects/1/edit
  def edit
    @project = Project.find(params[:ids])
  end

  # POST /projects
  # POST /projects.xml
  def create
    @project = Project.new(params[:project])

    respond_to do |format|
      if @project.save
        flash[:notice] = 'Project was successfully created.'
        format.html { redirect_to(:action=>'show', :ids=>@project) }
        format.xml  { render :xml => @project, :status => :created, :location => @project }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @project.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /projects/1
  # PUT /projects/1.xml
  def update
    @project = Project.find(params[:ids])

    respond_to do |format|
      if @project.update_attributes(params[:project])
        flash[:notice] = 'Project was successfully updated.'
        format.html { redirect_to(:action=>'show', :ids=>@project) }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @project.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /projects/1
  # DELETE /projects/1.xml
  def destroy
    Project.destroy params[:id].split(',')

    respond_to do |format|
      format.html { redirect_to(projects_url) }
      format.xml  { head :ok }
    end
  end
end
