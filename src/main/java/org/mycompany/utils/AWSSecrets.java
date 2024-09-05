package org.mycompany.utils;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;

public class AWSSecrets {

	public static String GetSecrets(String secretName, String accessKey, String secretKey) {

		Region region = Region.US_WEST_1;
		SecretsManagerClient secretsClient;

		AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
		secretsClient = SecretsManagerClient.builder().credentialsProvider(StaticCredentialsProvider.create(awsCreds))
				.region(region).build();

		try {

			GetSecretValueRequest valueRequest = GetSecretValueRequest.builder().secretId(secretName).build();

			GetSecretValueResponse valueResponse = secretsClient.getSecretValue(valueRequest);
			String secret = valueResponse.secretString();

			return secret;

		} catch (SecretsManagerException e) {
			throw new RuntimeException(e.awsErrorDetails().errorMessage());
		} finally {
			secretsClient.close();
		}
	}

}