name 'app_server'
description 'Application server'

run_list [ 
  'recipe[java]' 
]

default_attributes({
  java: {
    accept_license_agreement:   true,
    oracle: {
      accept_oracle_download_terms: true
    },
    install_flavor: 'oracle',
    jdk_version: '7'
  }
})
