package es.seresco.cursojee.gestbifly;

import java.util.function.Consumer;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.lang.NonNull;

/**
 * Utilities used for testing something.
 */
public class TestingUtils {

	/**
	 * A Mockito {@literal Answer} allowing return the first argument.
	 *
	 * @param <T> datatype to return
	 * @param clazz - type to return / cast, never {@literal null}
	 * @return an Answer, never {@literal null}
	 * @see InvocationOnMock#getArgument(int, Class)
	 */
	public static @NonNull <T> Answer<T> identityAnswer(
			final @NonNull Class<T> clazz) {
		return argsAnswer(clazz, 0);
	}

	/**
	 * A Mockito {@literal Answer} allowing return the argument at desired
	 * {@literal index}
	 *
	 * @param <T> datatype to return
	 * @param clazz - type to return / cast, never {@literal null}
	 * @param argIndex - the index, a positive or zero number
	 * @return an Answer, never {@literal null}
	 * @see InvocationOnMock#getArgument(int, Class)
	 */
	public static @NonNull <T> Answer<T> argsAnswer(
			final @NonNull Class<T> clazz, final int argIndex) {
		return invocation -> invocation.getArgument(argIndex, clazz);
	}

	/**
	 * A Mockito {@literal Answer} allowing return the first argument
	 * with facilities to execute an {@literal action} over it after
	 * resolve its value.
	 *
	 * @param <T> datatype to return
	 * @param clazz - type to return / cast, never {@literal null}
	 * @param action - the action, never {@literal null}
	 * @return an Answer, never {@literal null}
	 * @see InvocationOnMock#getArgument(int, Class)
	 */
	public static @NonNull <T> Answer<T> identityAnswer(
			final @NonNull Class<T> clazz,
			final @NonNull Consumer<T> action)
	{
		return argsAnswer(clazz, 0, action);
	}

	/**
	 * A Mockito {@literal Answer} allowing return the argument at desired
	 * {@literal index} with facilities to execute an {@literal action}
	 * over it after resolve its value.
	 *
	 * @param <T> datatype to return
	 * @param clazz - type to return / cast, never {@literal null}
	 * @param argIndex - the index, a positive or zero number
	 * @param action - the action, never {@literal null}
	 * @return an Answer, never {@literal null}
	 * @see InvocationOnMock#getArgument(int, Class)
	 */
	public static @NonNull <T> Answer<T> argsAnswer(
			final @NonNull Class<T> clazz, final int argIndex,
			final @NonNull Consumer<T> action)
	{
		return invocation -> {
			T arg = invocation.getArgument(argIndex, clazz);
			action.accept(arg);
			return arg;
		};
	}

}
