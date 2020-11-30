#!/usr/bin/env fish

function do_func
  set -l _spring_project spring-cloud-gcp-security-gs
  set -l _spring_project_path (pwd |awk -F "/$_spring_project" '{print $1}')/$_spring_project

  argparse -n do_func 'h/help' 'p/project=' 'r/registry=' 'n/name=' 't/tag=' -- $argv
  or return 1

  if set -lq _flag_help
    echo "generate-gradle-props.fish -p/--project <GCP_PROJECT_ID> -r/--registry <REGISTRY_NAME> -n/--name <APP_NAME> -t/--tag <TAG>"
    return
  end

  set -lq _flag_project
  or set -l _flag_project (gcloud config get-value project)
  
  set -lq _flag_registry
  or set -l _flag_registry "container"

  set -lq _flag_name
  or set -l _flag_name "demo-app"

  set -lq _flag_tag
  or set -l _flag_tag (date '+%Y%m%d')

  echo "gcp_project_id=$_flag_project" > $_spring_project_path/gradle.properties
  echo "registry_name=$_flag_registry" >> $_spring_project_path/gradle.properties
  echo "app_name=$_flag_name" >> $_spring_project_path/gradle.properties
  echo "app_tag=$_flag_tag" >> $_spring_project_path/gradle.properties
end

do_func $argv

