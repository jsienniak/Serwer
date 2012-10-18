package pl.zpi.server._trash;

import pl.zpi.server.test.DummySerializer;

public class ITokenResolverClassLoader implements ITokenResolver {

	@Override
	public String resolveToken(String tokenName) {
		try {
			Class<DummySerializer> s = (Class<DummySerializer>) Class.forName(tokenName);
			DummySerializer ser = s.newInstance();
			return ser.getNode();
		} catch (ClassNotFoundException e) {
			System.out.println("Token name: "+tokenName);
			e.printStackTrace();
		} catch (InstantiationException e) {
			System.out.println("Token name: "+tokenName);
			e.printStackTrace();
		} catch (IllegalAccessException e) {

			e.printStackTrace();
		}
		return null;
	}

}
