package jlhuangliang;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Objects;

public class APIGatewayAuthorizerHandler implements RequestHandler<TokenAuthorizerContext, AuthPolicy> {

  @Override
  public AuthPolicy handleRequest(TokenAuthorizerContext input, Context context) {

    String token = input.getAuthorizationToken();

    String principalId = "3";

    if (!Objects.equals(token, "2fdde7b21b6a4a4fb5fbe47475a36dcc")) {
      throw new RuntimeException("Unauthorized......");
    }

    String methodArn = input.getMethodArn();
    String[] arnPartials = methodArn.split(":");
    String region = arnPartials[3];
    String awsAccountId = arnPartials[4];
    String[] apiGatewayArnPartials = arnPartials[5].split("/");
    String restApiId = apiGatewayArnPartials[0];
    String stage = apiGatewayArnPartials[1];

    return new AuthPolicy(principalId, AuthPolicy.PolicyDocument.getAllowAllPolicy(region, awsAccountId, restApiId, stage));
  }

}
