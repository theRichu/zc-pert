class GgroupsController < ApplicationController
  # GET /ggroups
  # GET /ggroups.xml
  def index
    @ggroups = Ggroup.all

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @ggroups }
    end
  end

  # GET /ggroups/1
  # GET /ggroups/1.xml
  def show
    @ggroup = Ggroup.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @ggroup }
    end
  end

  # GET /ggroups/new
  # GET /ggroups/new.xml
  def new
    @ggroup = Ggroup.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @ggroup }
    end
  end

  # GET /ggroups/1/edit
  def edit
    @ggroup = Ggroup.find(params[:id])
  end

  # POST /ggroups
  # POST /ggroups.xml
  def create
    @ggroup = Ggroup.new(params[:ggroup])

    respond_to do |format|
      if @ggroup.save
        flash[:notice] = 'Ggroup was successfully created.'
        format.html { redirect_to(@ggroup) }
        format.xml  { render :xml => @ggroup, :status => :created, :location => @ggroup }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @ggroup.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /ggroups/1
  # PUT /ggroups/1.xml
  def update
    @ggroup = Ggroup.find(params[:id])

    respond_to do |format|
      if @ggroup.update_attributes(params[:ggroup])
        flash[:notice] = 'Ggroup was successfully updated.'
        format.html { redirect_to(@ggroup) }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @ggroup.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /ggroups/1
  # DELETE /ggroups/1.xml
  def destroy
    @ggroup = Ggroup.find(params[:id])
    @ggroup.destroy

    respond_to do |format|
      format.html { redirect_to(ggroups_url) }
      format.xml  { head :ok }
    end
  end
end
