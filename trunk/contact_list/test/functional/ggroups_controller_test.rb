require 'test_helper'

class GgroupsControllerTest < ActionController::TestCase
  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:ggroups)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create ggroup" do
    assert_difference('Ggroup.count') do
      post :create, :ggroup => { }
    end

    assert_redirected_to ggroup_path(assigns(:ggroup))
  end

  test "should show ggroup" do
    get :show, :id => ggroups(:one).to_param
    assert_response :success
  end

  test "should get edit" do
    get :edit, :id => ggroups(:one).to_param
    assert_response :success
  end

  test "should update ggroup" do
    put :update, :id => ggroups(:one).to_param, :ggroup => { }
    assert_redirected_to ggroup_path(assigns(:ggroup))
  end

  test "should destroy ggroup" do
    assert_difference('Ggroup.count', -1) do
      delete :destroy, :id => ggroups(:one).to_param
    end

    assert_redirected_to ggroups_path
  end
end
