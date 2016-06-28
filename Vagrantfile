VAGRANTFILE_API_VERSION = "2"
Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
  config.ssh.forward_agent = true
  config.vm.box = "boxcutter/ubuntu1404"
  config.vm.synced_folder "~/.identity", "/home/vagrant/.identity", create: true
  config.vm.synced_folder "~/.gnupg", "/home/vagrant/.gnupg", create: true
  config.vm.provider "virtualbox" do |vb|
    vb.customize ["modifyvm", :id, "--natdnshostresolver1", "on"]
    vb.customize ["modifyvm", :id, "--natdnsproxy1", "on"]
  end
  config.vm.provision "shell", path: "https://s3-us-west-1.amazonaws.com/raptr-us-west-1/bootstrap"

  # box-specific
  config.vm.provision "shell", inline: $provision
end

$provision = <<-EOF
  apt-get install -y maven openjdk-7-jdk
  sed -i -r "s%<servers>%<servers>\
  <server>\
    <id>ossr</id>\
    <username>smartystreets</username>\
    <password>$OSSRH_PASSWORD</password>\
  </server>%g" /etc/maven/settings.xml
  sed -i -r "s%<profiles>%<profiles><profile><id>ossrh</id><activation><activeByDefault>true</activeByDefault></activation><properties><gpg.executable>gpg</gpg.executable><gpg.passphrase>password</gpg.passphrase></properties></profile>%g" /etc/maven/settings.xml
EOF
