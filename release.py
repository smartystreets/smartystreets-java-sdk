#!/usr/bin/env python

import base64
import json
import mimetypes
import os
import subprocess
import sys
import urllib2


REPO_NAME = os.path.basename(os.getcwd())
CREATE_RELEASE_URL = 'https://api.github.com/repos/smartystreets/{0}/releases'.format(REPO_NAME)
LIST_RELEASE_URL = CREATE_RELEASE_URL + '/{0}'
UPLOAD_ASSET_URL = CREATE_RELEASE_URL.replace('api.', 'uploads.') + '/{0}/assets?name={1}'
CREATE_FAILURE = 'Failed to create release: {0} {1}'
UPLOAD_FAILURE = 'Failed to upload asset ({0}). HTTP Response: {1} {2}'
GITHUB_API_TOKEN = os.getenv('GITHUB_API_TOKEN')
DEFAULT_MIME_TYPE = 'plain/text'


def main():
	assert GITHUB_API_TOKEN, 'Required environment variable: GITHUB_API_TOKEN'
	version = subprocess.check_output('git describe', shell=True).strip()

	# Create the release on github:
	data = json.dumps({'tag_name': version, 'name': version})
	request = urllib2.Request(CREATE_RELEASE_URL, data=data)
	request.add_header('Content-Type', 'application/json')
	request.get_method = lambda: 'POST'
	response = send(request)
	assert response.code == 201, CREATE_FAILURE.format(
		response.code, response.msg)

	body = json.loads(response.read())
	release_id = body['id']

	request = urllib2.Request(LIST_RELEASE_URL.format(release_id))
	response = send(request)
	assert response.code == 200, 'boink: {0} {1}'.format(response.code, response.msg)

	# upload any assets specified by the command line:
	for asset in sys.argv:
		if asset == __file__:
			continue
		if os.path.exists(asset):
			print 'Uploading asset:', asset
			with open(asset) as data:
				asset_name = os.path.basename(asset)
				url = UPLOAD_ASSET_URL.format(release_id, asset_name)
				print url
				request = urllib2.Request(url, data.read())
				type_, encoding = mimetypes.guess_type(asset)
				request.add_header('Content-Type', type_ or DEFAULT_MIME_TYPE)
				request.get_method = lambda: 'POST'
				response = send(request)
				assert response.code == 201, UPLOAD_FAILURE.format(
							asset, response.code, response.msg)

	print 'Release created and assets uploaded.'


def send(request):
	auth = base64.b64encode('smartystreets-jenkins:{0}'.format(GITHUB_API_TOKEN))
	request.add_header('Authorization', 'Basic {0}'.format(auth))

	try:
		return urllib2.urlopen(request)
	except urllib2.HTTPError as e:
		print e.info()
		print e.read()
		raise


if __name__ == '__main__':
	main()
