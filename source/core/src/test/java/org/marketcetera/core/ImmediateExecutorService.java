package com.marketcetera.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ImmediateExecutorService implements ExecutorService {


	public boolean awaitTermination(long timeout, TimeUnit unit)
			throws InterruptedException {
		return true;
	}

	public <T> List<Future<T>> invokeAll(Collection<Callable<T>> tasks)
			throws InterruptedException {
		throw new UnsupportedOperationException();
	}

	public <T> List<Future<T>> invokeAll(Collection<Callable<T>> tasks,
			long timeout, TimeUnit unit) throws InterruptedException {
		throw new UnsupportedOperationException();
	}

	public <T> T invokeAny(Collection<Callable<T>> tasks)
			throws InterruptedException, ExecutionException {
		throw new UnsupportedOperationException();
	}

	public <T> T invokeAny(Collection<Callable<T>> tasks, long timeout,
			TimeUnit unit) throws InterruptedException, ExecutionException,
			TimeoutException {
		throw new UnsupportedOperationException();
	}

	public boolean isShutdown() {
		return false;
	}

	public boolean isTerminated() {
		return false;
	}

	public void shutdown() {
	}

	public List<Runnable> shutdownNow() {
		return new ArrayList<Runnable>();
	}

	public <T> Future<T> submit(Callable<T> task) {
		if (task == null) throw new NullPointerException();
		T result;
		try {
			result = task.call();
		} catch (Exception ex){
			return new ImmediateFuture<T>(ex, true);
		}
		return new ImmediateFuture<T>(result, false);
	}

	public Future<?> submit(Runnable task) {
		if (task == null) throw new NullPointerException();
		try {
			task.run();
		} catch (Exception ex){
			return new ImmediateFuture<Object>(ex, true);
		}
		return new ImmediateFuture<Object>(null, false);
	}

	public <T> Future<T> submit(Runnable task, T result) {
		if (task == null) throw new NullPointerException();
		try {
			task.run();
		} catch (Exception ex){
			return new ImmediateFuture<T>(ex, true);
		}
		return new ImmediateFuture<T>(result, false);
	}

	public void execute(Runnable command) {
		command.run();
	}

	public class ImmediateFuture<T> implements Future<T> {


		private final T result;
		private final Exception exception;

		public ImmediateFuture(T result, boolean errorOccurred) {
			this.result = result;
			this.exception = null;
		}

		public ImmediateFuture(Exception ex, boolean errorOccurred) {
			this.result = null;
			this.exception = ex;
		}

		public boolean cancel(boolean mayInterruptIfRunning) {
			return false;
		}

		public T get() throws InterruptedException, ExecutionException {
			if (exception != null){
				throw new ExecutionException(exception);
			}
			return result;
		}

		public T get(long timeout, TimeUnit unit) throws InterruptedException,
				ExecutionException, TimeoutException {
			return get();
		}

		public boolean isCancelled() {
			return false;
		}

		public boolean isDone() {
			return true;
		}

	}

}
