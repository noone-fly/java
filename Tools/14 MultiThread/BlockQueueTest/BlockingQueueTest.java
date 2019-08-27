package com.pierre.blockingqueue;

import java.awt.geom.QuadCurve2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.swing.text.AbstractDocument.DefaultDocumentEvent;

public class BlockingQueueTest {

	/**
	 * 程序演示如何使用阻塞队列来控制线程集
	 * 程序在一个目录以及它的所有子目录下面搜索所有文件，打印出包含指定关键字的行
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		System.out.println("Enter base directory (e.g. /Library/Java/JavaVirtualMachines/jdk1.8.0_161.jdk/Contents/Home)");
		String directory = in.nextLine();
		System.out.println("Enter keyboard (e.g. valatile):");
		String keyboard = in.nextLine();
		
		final int FILE_QUEUE_SIZE = 10;
		final int SEARCH_THREADS = 100;
		
		BlockingQueue<File> queue = new ArrayBlockingQueue<File>(FILE_QUEUE_SIZE);
		FileEnumerationTask enumerator = new FileEnumerationTask(queue, new File(directory));
		new Thread(enumerator).start();
		for (int i = 0; i <= SEARCH_THREADS; i++) {
			new Thread(new SearchTask(queue, keyboard)).start();;
		}
	}

}

/**
 * this task enumerates all files in a directory and its subderecotries 
 * 列举所有文件以及子目录下所有文件
 * 生产者线程枚举在所有子目录下的所有文件，并把它们放到一个阻塞队列中，这个操作很快，如果没有上限，很快就包含所有找到的文件
 * @author chenpiyang
 *
 */
class FileEnumerationTask implements Runnable{
	//创建一个空文件，作为搜索结束标志
	public static File DUMMY = new File("");
	private BlockingQueue<File> queue;
	private File startingDirectory;
	
	public FileEnumerationTask(BlockingQueue<File> queue, File startingDirecotry) {
		this.queue = queue;
		this.startingDirectory = startingDirecotry;
	}


	@Override
	public void run() {
		try {
			enumerate(startingDirectory);
			//等该目录下的文件和子目录的文件都检索完毕，就在阻塞队列最后加入一个空文件。作为结束标志。
			queue.put(DUMMY);
		} catch (InterruptedException e) {
			// TODO: handle exception
		}
	}
	
	//枚举所以文件并放入到阻塞队列中，
	public void enumerate(File directory) throws InterruptedException{
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				//如果是目录，就递归调用继续检索子目录的文件和目录，
				enumerate(file);
			}else {
				//如果非目录，就把文件加入到阻塞队列中
				queue.put(file);
			}
		}
	}
}

/**
 * searches a file for a given keyword and prints all matching lines
 * 同时启动大量搜索线程，每个搜索线程从队列中取出一个文件，打开，打印所有包含关键字的行，然后取出下一个文件，
 * 这里使用了一个小技巧，在工作结束后终止这个应用程序，为了发出完成信号，枚举线程放置一个虚拟对象到队列中，
 * @author chenpiyang
 *
 */
class SearchTask implements Runnable{
	private BlockingQueue<File> queue;
	private String keyboard;
	
	public SearchTask(BlockingQueue<File> queue, String keyboard) {
		this.queue = queue;
		this.keyboard = keyboard;
	}

	@Override
	public void run() {
		try {
			boolean done = false;
			while (!done) {
				File file = queue.take();
				if (file == FileEnumerationTask.DUMMY) {
					queue.put(file);
					done = true;
				}else {
					search(file);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO: handle exception
		}
	}
	
	public void search(File file) throws IOException{
		Scanner in = new Scanner(new FileInputStream(file));
		int lineNumber = 0;
		while (in.hasNextLine()) {
			lineNumber++;
			String line = in.nextLine();
			if (line.contains(keyboard)) {
				System.out.printf("%s:%d:%s%n", file.getPath(), lineNumber, line);
			}
		}
		in.close();
	}
}


