use std::sync::Arc;
use std::thread;
use std::thread::{JoinHandle, Thread};
use std::time::Duration;
use jni::JNIEnv;
use jni::objects::{GlobalRef, JMethodID, JObject};
use jni::signature::ReturnType;
use jni::sys::{_jobject, jint, jlong, jobject};

pub static mut STORAGE: Option<Vec<FuturePromise>> = None;

pub struct FuturePromise<'a> {
    handle: jlong,
    pub thread_handle: Option<JoinHandle<&'a mut _jobject>>
}

impl FuturePromise<'_> {

    pub fn new_storage() {
        unsafe { STORAGE = Some(vec![]) }
    }

    pub fn storage() -> &'static mut Vec<FuturePromise<'static>> {
        unsafe { STORAGE.as_mut().expect("unable to get storage!") }
    }

    pub fn of<'a>(handle: jlong) -> Option<&'static mut FuturePromise<'a>> {
        for item in FuturePromise::storage() {
            if item.handle == handle {
                return Some(item);
            }
        }
        None
    }

    pub fn execute_d(env: JNIEnv, handle: jlong, runnable: JObject, run_method_id: JMethodID) {
        let jvm = env.get_java_vm().unwrap();
        let runnable = env.new_global_ref(runnable).unwrap();
        let thread_handle: JoinHandle<&'_ mut _jobject> = thread::spawn(move || unsafe {
            let env = jvm.attach_current_thread().unwrap();
            let result =  env.call_method_unchecked(&runnable, run_method_id, ReturnType::Object, &[]).unwrap().l().expect("unable to call method!");
            result.as_mut().unwrap()
        });
        FuturePromise::storage().push(FuturePromise{handle, thread_handle: Some(thread_handle) })
    }

    pub fn execute_e(env: JNIEnv, handle: jlong, runnable: JObject, run_method_id: JMethodID, d: jint) {
        let jvm = env.get_java_vm().unwrap();
        let runnable = env.new_global_ref(runnable).unwrap();
        let thread_handle: JoinHandle<&'_ mut _jobject> = thread::spawn(move || unsafe {
            thread::sleep(Duration::from_millis(d as u64));
            let env = jvm.attach_current_thread().unwrap();
            let result =  env.call_method_unchecked(&runnable, run_method_id, ReturnType::Object, &[]).unwrap().l().expect("unable to call method!");
            result.as_mut().unwrap()
        });
        FuturePromise::storage().push(FuturePromise{handle, thread_handle: Some(thread_handle) })
    }

}