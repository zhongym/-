package com.zhong.easyquery.Fragment;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import com.zhong.easyquery.BaseActivity;
import com.zhong.easyquery.domain.Account;
import com.zhong.easyquery.utils.NetUtil;
import com.zhong.easyquery.utils.NoticetManager;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

	protected View contentView;

	protected Context context;

	private FinalHttp finalHttp;

	private boolean show = true;
	
	/**
	 * ��ЩFragment��ʾʱ�ŵ��� initData()�����������ݣ���ֹԤ�����˷�����
	 */
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser && isVisible() && contentView.getVisibility() == View.VISIBLE&&show) {
			System.out.println("initData()");
			initData(); // �������ݵķ���
			show = false;
		}
		super.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		finalHttp = new FinalHttp();
		context = getActivity();
		System.out.println(this.getClass().getName() + ".onCreate()");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// ��ֹ��γ�ʼ��view
		if (contentView != null) {
			ViewGroup parent = (ViewGroup) contentView.getParent();
			if (parent != null) {
				parent.removeView(contentView);
			}
		} else {
			contentView = initView(inflater);// �ؼ���ʼ��
			setListener();
			System.out.println(this.getClass().getName() + ".onCreateView()");
		}
		return contentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		if (getUserVisibleHint() && contentView.getVisibility() == View.VISIBLE&&show) {
			System.out.println("onActivityCreated-initData()");
			initData();
			show=false;
		}
		super.onActivityCreated(savedInstanceState);
		
		System.out.println(this.getClass().getName() + ".onActivityCreated()");
	}

	@Override
	public void onDestroy() {
		System.out.println(this.getClass().getName() + ".onDestroy()");
		super.onDestroy();
	}

	public abstract void initData();

	public abstract View initView(LayoutInflater inflater);

	/**
	 * ��Ӽ����¼�
	 */
	public void setListener() {

	}

	public View getContentView() {
		return contentView;
	}

	public Context getContext() {
		return context;
	}

	/**
	 * ���ô˷�����������������ݣ�������ɺ�ص�onRequestDataComplete����
	 * 
	 * @param url
	 * @param params
	 */
	public void requestData(String url, AjaxParams params) {

		if (!NetUtil.checkNetConnetion(getActivity())) {
			NoticetManager.showNoNetWork(getActivity());
			return;
		}

		// ��������url����Ҫ�Ĳ���
		params.put("sblx", "1");
		params.put("xtbb", "1.3.0");
		params.put("sbxh", Build.DEVICE); // �豸�ͺ�
		params.put("sbbb", Build.VERSION.RELEASE); // �豸�汾

		// �ǵ�¼url��Ҫ�Ĳ���
		Account account = Account.getInstaceFromSerializable(getActivity());
		if (account != null) {
			params.put("userID", account.userID);
			params.put("userType", account.userType);
			params.put("c_session_key", account.c_session_key);
		}

		finalHttp.post(url, params, new AjaxCallBack<String>() {
			@Override
			public void onFailure(Throwable t, int errorNo, final String strMsg) {
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						onRequestDataFailure(strMsg);
					}
				});
			}

			@Override
			public void onSuccess(final String data) {
				try {
					final JSONObject object = new JSONObject(data);
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							onRequestDataSuccess(object);
						}
					});

				} catch (JSONException e) {
					e.printStackTrace();
					onRequestDataFailure("���ݽ�����������");
				}

			}
		});
	}

	/**
	 * ������requestData��������ʧ�ܺ�ص� ���������ui�߳�ִ��
	 * 
	 * @param strMsg
	 *            ʧ����Ϣ
	 */
	public void onRequestDataFailure(String strMsg) {
		if (strMsg==null||strMsg.length()==0) {
			showToast("��������ʧ�ܣ�����");
		}
		showToast(strMsg);
	}

	/**
	 * ������requestData�������ݳɹ���ص� ���������ui�߳�ִ��
	 * 
	 * @param object
	 *            �ӷ��������ص�����
	 */
	public void onRequestDataSuccess(JSONObject object) {

	}

	public void showToast(String msg) {
		NoticetManager.showToast(getActivity(), msg);
	}

}
