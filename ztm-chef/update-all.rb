#!/usr/bin/env ruby

LOCAL_COOKBOOK="kdp-ztm"

COOKBOOKS_PATH="#{File.dirname(__FILE__)}/cookbooks"

require 'trollop'
require 'simply_useful/cli'
include SimplyUseful::Cli

$opts = Trollop::options do
  banner <<-EOS
Updates roles, environments and cookbooks 
EOS
  
  opt :all_cookbooks, "Upload all cookbooks (without using `force`)", default: false
  opt :foodcritic, "Critic the cookbooks", default: true
end

bundler_run("foodcritic #{COOKBOOKS_PATH}/#{LOCAL_COOKBOOK}") if $opts[:foodcritic]

# Ordering is important. Otherwise version constraints are not applied by Berkshelf
bundler_run("knife environment from file environments/*.rb")
bundler_run("knife role from file roles/*.rb")

if $opts[:all_cookbooks]
  bundler_run('berks upload')
  bundler_run('berks apply production')
  bundler_run('berks apply staging')
else
  bundler_run("knife cookbook upload --force #{LOCAL_COOKBOOK}")
end
