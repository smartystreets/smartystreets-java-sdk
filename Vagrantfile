VAGRANTFILE_API_VERSION = "2"
Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
  config.ssh.forward_agent = true
  config.vm.box = "bento/ubuntu-18.04"
  config.vm.synced_folder "~/.identity", "/home/vagrant/.identity", create: true
  config.vm.synced_folder "~/.gnupg", "/home/vagrant/.gnupg", create: true
  config.vm.provider "virtualbox" do |vb|
    vb.customize ["modifyvm", :id, "--nictype1", "Am79C973"]
  end
  config.vm.provision "shell", path: "https://s3-us-west-1.amazonaws.com/raptr-us-west-1/baseline/roles/vagrant"

  # box-specific
  config.vm.provision "shell", inline: $provision
end

$provision = <<-EOF
  apt-get install -y maven openjdk-7-jdk

  sed -i -r "s/#default-key 621CC013/default-key F39CD1E9/g" /home/vagrant/.gnupg/gpg.conf

  sed -i -r "s%<servers>%<servers>\
  <server>\
    <id>ossrh</id>\
    <username>smartystreets</username>\
    <password>$OSSRH_PASSWORD</password>\
  </server>%g" /etc/maven/settings.xml

  sed -i -r "s%<profiles>%<profiles>\
  <profile>\
    <id>ossrh</id>\
    <activation>\
      <activeByDefault>true</activeByDefault>\
    </activation>\
    <properties>\
      <gpg.executable>gpg</gpg.executable>\
    </properties>\
  </profile>%g" /etc/maven/settings.xml
EOF
